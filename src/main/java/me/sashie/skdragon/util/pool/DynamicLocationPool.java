package me.sashie.skdragon.util.pool;

import me.sashie.skdragon.util.DynamicLocation;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class DynamicLocationPool {

    private final ObjectPool<DynamicLocation> pool;

    public DynamicLocationPool() {
        pool = new ObjectPool<>(() -> new DynamicLocation());
    }

    public DynamicLocation acquire(Object center) {
        DynamicLocation dynamicLocation = pool.acquire();
        if (center instanceof Entity) {
            dynamicLocation.init((Entity) center);
            return dynamicLocation;
        } else if (center instanceof Location) {
            dynamicLocation.init((Location) center);
            return dynamicLocation;
        } else throw new IllegalArgumentException("[skDragon] The input object is not of type Entity or Location");
    }

    public DynamicLocation acquire() {
        return pool.acquire();
    }

    public DynamicLocation acquire(Location location) {
        DynamicLocation dynamicLocation = pool.acquire();
        dynamicLocation.init(location);
        return dynamicLocation;
    }

    public DynamicLocation acquire(Entity entity) {
        DynamicLocation dynamicLocation = pool.acquire();
        dynamicLocation.init(entity);
        return dynamicLocation;
    }

    public void release(DynamicLocation dynamicLocation) {
        if (dynamicLocation == null) return;
        dynamicLocation.reset();
        pool.release(dynamicLocation);
    }
}
