package be.baes.thisDevelopersLifePlayer.dal;

import java.util.List;

public interface PodCastAdapter {

    List<be.baes.thisDevelopersLifePlayer.model.PodCast> getAllItems();

    boolean updatePodCast(be.baes.thisDevelopersLifePlayer.model.PodCast podCast);

    long insertPodCast(be.baes.thisDevelopersLifePlayer.model.PodCast podCast);

    boolean deleteAll();
    
    int numberOfPodcasts();
    
    List<be.baes.thisDevelopersLifePlayer.model.PodCast> getAllItems(Integer pageFrom, Integer pageTo);

    List<be.baes.thisDevelopersLifePlayer.model.PodCast> findItems(String s);
}
