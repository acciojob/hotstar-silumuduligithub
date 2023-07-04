package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){

        //Save The subscription Object into the Db and return the total Amount that user has to pay
        User user = userRepository.findById(subscriptionEntryDto.getUserId()).get();
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        Integer amount = 0;
        if(subscriptionEntryDto.getSubscriptionType().equals(SubscriptionType.BASIC)){
            amount = 500 + (200*subscriptionEntryDto.getNoOfScreensRequired());
        }else if(subscriptionEntryDto.getSubscriptionType().equals(SubscriptionType.PRO)){
            amount = 800 + (250*subscriptionEntryDto.getNoOfScreensRequired());
        }else{
            amount = 1000 + (350*subscriptionEntryDto.getNoOfScreensRequired());
        }
        subscription.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
        subscription.setTotalAmountPaid(amount);
        subscriptionRepository.save(subscription);
       return amount;
    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository
        User user = userRepository.findById(userId).get();
        if(user.getSubscription().getSubscriptionType().equals(SubscriptionType.ELITE)){
            throw new Exception("Already the best Subscription");
        }
        int amount = 0;
        if(user.getSubscription().getSubscriptionType().equals(SubscriptionType.BASIC)){
            Subscription subscription = user.getSubscription();
            subscription.setSubscriptionType(SubscriptionType.PRO);
            user.setSubscription(subscription);
            amount = 800 - 500;
            subscription.setTotalAmountPaid(amount);

        }if(user.getSubscription().getSubscriptionType().equals(SubscriptionType.PRO)){
            Subscription subscription = user.getSubscription();
            subscription.setSubscriptionType(SubscriptionType.PRO);
            user.setSubscription(subscription);
            amount = 1000 - 800;
            subscription.setTotalAmountPaid(amount);
        }
        userRepository.save(user);
        return amount;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb
        Integer totalAmount = 0;
        List<User> userList = userRepository.findAll();
        for(User user : userList){
            totalAmount += user.getSubscription().getTotalAmountPaid();
        }
        return totalAmount;
    }

}
