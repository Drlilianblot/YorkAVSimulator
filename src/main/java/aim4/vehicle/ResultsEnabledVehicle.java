package aim4.vehicle;

import aim4.map.merge.RoadNames;

/**
 * Created by Callum on 21/04/2017.
 */
public interface ResultsEnabledVehicle {
    double getFinishTime();

    double getDelay();

    double getFinalVelocity();

    double getMaxVelocity();

    double getMinVelocity();

    double getFinalXPos();

    double getFinalYPos();

    double getStartTime();

    RoadNames getStartingRoad();

    void setFinishTime(double finishTime);

    void setDelay(double delay);

    void setFinalVelocity(double finalVelocity);

    void setMaxVelocity(double maxVelocity);

    void setMinVelocity(double minVelocity);

    void setFinalXPos(double xPos);

    void setFinalYPos(double yPos);

    void setStartTime(double startTime);

    void setStartingRoad(RoadNames roadName);
}
