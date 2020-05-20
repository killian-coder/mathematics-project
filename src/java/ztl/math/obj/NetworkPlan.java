/*
 * This Application and all Its resources are the property of
 * John Mtonga(Application Developer)
 */
package ztl.math.obj;

/**
 *
 * @author user
 */
public enum NetworkPlan {
    OnNetOffer(1, "On Net Offer"),
    AllNetOffer(2, "All Network Offer");

    private final int ID;
    private final String description;

    private NetworkPlan(int ID, String description) {
        this.ID = ID;
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }

    public static NetworkPlan getNetworkPlan(int menuID) {
        for (NetworkPlan m : NetworkPlan.values()) {
            if (m.getID() == menuID) {
                return m;
            }
        }
        return null;
    }
}
