package com.driver.services;

import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSeriesService {

    @Autowired
    WebSeriesRepository webSeriesRepository;

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addWebSeries(WebSeriesEntryDto webSeriesEntryDto)throws  Exception{

        //Add a webSeries to the database and update the ratings of the productionHouse
        //Incase the seriesName is already present in the Db throw Exception("Series is already present")
        //use function written in Repository Layer for the same
        //Dont forget to save the production and webseries Repo
       List<WebSeries> webSeriesList = webSeriesRepository.findAll();
       for(WebSeries webSeries : webSeriesList){
           if(webSeries.getSeriesName().equals(webSeriesEntryDto.getSeriesName())){
               throw  new Exception("Series is already present");
           }
       }
       WebSeries webSeries = new WebSeries();
       webSeries.setSeriesName(webSeriesEntryDto.getSeriesName());
        ProductionHouse productionHouse = productionHouseRepository.findById(webSeriesEntryDto.getProductionHouseId()).get();
       webSeries.setAgeLimit(webSeriesEntryDto.getAgeLimit());
       webSeries.setRating(webSeriesEntryDto.getRating());
       double ratting = 0.0;
       List<WebSeries>webSeries1 = productionHouse.getWebSeriesList();
       int size = webSeries1.size();
       for(WebSeries werseries : webSeries1){
           ratting += werseries.getRating();
       }
       ratting += webSeriesEntryDto.getRating();
       size += 1;
       ratting = ratting / size;
       productionHouse.setRatings(ratting);
       webSeries.setProductionHouse(productionHouse);
       productionHouse.getWebSeriesList().add(webSeries);
       webSeriesRepository.save(webSeries);
       productionHouseRepository.save(productionHouse);
       return webSeries.getId();
    }

}
