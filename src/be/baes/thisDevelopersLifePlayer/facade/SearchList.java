package be.baes.thisDevelopersLifePlayer.facade;

import java.util.Observer;

/**
 * Created by IntelliJ IDEA.
 * User: christiaan
 * Date: 31/12/11
 * Time: 8:56
 * To change this template use File | Settings | File Templates.
 */
public interface SearchList {
    void search(String query);

    void addObserver(Observer observer);

    void updateList(be.baes.thisDevelopersLifePlayer.model.FillListResult result);

    void deleteObserver(Observer observer);

    void search(String query, int position);
}
