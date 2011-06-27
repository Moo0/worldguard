// $Id$
/*
 * WorldGuard
 * Copyright (C) 2010 sk89q <http://www.sk89q.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

package com.sk89q.worldguard.protection;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.protection.managers.FlatRegionManager;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.GlobalProtectedRegion;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegionIntersectionTest {

    RegionManager manager;
    ProtectedRegion globalRegion;
    ProtectedRegion region1;
    ProtectedRegion region2;
    List<ProtectedRegion> regions1 = new ArrayList<ProtectedRegion>();;
    List<ProtectedRegion> regions2 = new ArrayList<ProtectedRegion>();;

    @Before
    public void setUp() throws Exception {
        setUpGlobalRegion();

        manager = new FlatRegionManager(null);
    }

    void setUpGlobalRegion() {
        globalRegion = new GlobalProtectedRegion("__global__");
    }

    @Test
    public void testNonOverlap() throws Exception {
        ProtectedRegion region1 = new ProtectedCuboidRegion("1",
                new BlockVector(0, 0, 0), new BlockVector(5, 5, 5));
        ProtectedRegion region2 = new ProtectedCuboidRegion("2",
                new BlockVector(6, 0, 0), new BlockVector(11, 5, 5));

        regions1.add(region1);
        regions2.add(region2);

        assertFalse(region1.getIntersectingRegions(regions2).size() > 0);
        assertFalse(region2.getIntersectingRegions(regions1).size() > 0);
    }

    @Test
    public void testSideOverlap() throws Exception {
        ProtectedRegion region1 = new ProtectedCuboidRegion("1",
                new BlockVector(0, 0, 0), new BlockVector(5, 5, 5));
        ProtectedRegion region2 = new ProtectedCuboidRegion("2",
                new BlockVector(4, 0, 0), new BlockVector(10, 5, 5));

        regions1.add(region1);
        regions2.add(region2);

        assertTrue(region1.getIntersectingRegions(regions2).size() > 0);
        assertTrue(region2.getIntersectingRegions(regions1).size() > 0);
    }

    @Test
    public void testCornerOverlap() throws Exception {
        ProtectedRegion region1 = new ProtectedCuboidRegion("1",
                new BlockVector(0, 0, 0), new BlockVector(5, 5, 5));
        ProtectedRegion region2 = new ProtectedCuboidRegion("2",
                new BlockVector(2, 2, 2), new BlockVector(7, 7, 7));

        regions1.add(region1);
        regions2.add(region2);

        assertTrue(region1.getIntersectingRegions(regions2).size() > 0);
        assertTrue(region2.getIntersectingRegions(regions1).size() > 0);
    }

    @Test
    public void testContainingOverlap() throws Exception {
        ProtectedRegion region1 = new ProtectedCuboidRegion("1",
                new BlockVector(0, 0, 0), new BlockVector(5, 5, 5));
        ProtectedRegion region2 = new ProtectedCuboidRegion("2",
                new BlockVector(1, 1, 1), new BlockVector(4, 4, 4));

        regions1.add(region1);
        regions2.add(region2);

        assertTrue(region1.getIntersectingRegions(regions2).size() > 0);
        assertTrue(region2.getIntersectingRegions(regions1).size() > 0);
    }

    @Test
    public void testCrossingOverlap() throws Exception {
        ProtectedRegion region1 = new ProtectedCuboidRegion("1",
                new BlockVector(0, 0, 0), new BlockVector(5, 5, 5));
        ProtectedRegion region2 = new ProtectedCuboidRegion("2",
                new BlockVector(-1, 1, 1), new BlockVector(6, 4, 4));

        regions1.add(region1);
        regions2.add(region2);

        assertTrue(region1.getIntersectingRegions(regions2).size() > 0);
        assertTrue(region2.getIntersectingRegions(regions1).size() > 0);
    }


}
