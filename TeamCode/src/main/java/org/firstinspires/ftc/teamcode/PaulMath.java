package org.firstinspires.ftc.teamcode;

public class PaulMath {
    /**
     * Get the distance between two points
     * @param origin Point to calculate from
     * @param destination Point to calculate to
     * @return Distance between origin and destination
     */
    public static float distance(PointNd origin, PointNd destination) {
        return (float)Math.sqrt(((origin.getX()*origin.getX())+(origin.getY()*origin.getY()))+(destination.getZ()*destination.getZ()));
    }

    /**
     * Get the current location of robot
     * @param velocities_x array of all previous velocities in the x direction
     * @param velocities_y array of all previous velocities in the y direction
     * @return a 2 dimentional point location for the robot
     */
    public static PointNd location(float[] velocities_x, float[] velocities_y) {
        float x_initial = 0;
        float y_initial = 0;

        float delta_x = 0;
        float delta_y = 0;
        for (float velocity: velocities_x) {
            delta_x = delta_x + velocity;
        }
        for (float velocity: velocities_y) {
            delta_y = delta_y + velocity;
        }
        float x_current = x_initial + delta_x;
        float y_current = y_initial + delta_y;
        PointNd location = new PointNd(x_current, y_current);
        return location;

    }


}
