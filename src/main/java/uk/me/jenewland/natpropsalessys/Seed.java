package uk.me.jenewland.natpropsalessys;

import uk.me.jenewland.natpropsalessys.models.Branch;
import uk.me.jenewland.natpropsalessys.models.property.Property;
import uk.me.jenewland.natpropsalessys.models.property.PropertyFlat;
import uk.me.jenewland.natpropsalessys.models.property.PropertyHouse;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static uk.me.jenewland.natpropsalessys.Main.dataManager;
import static uk.me.jenewland.natpropsalessys.Main.logger;

public class Seed {
    public Seed() {
        List<Branch> branches = new ArrayList<>();
        List<Property> properties = new ArrayList<>();

        branches.add(new Branch("weymouth", "password", "Weymouth, Dorset, GB", "domain.tld/branches/weymouth", "weymouth@domain.tld", "07700900461"));
        branches.add(new Branch("dorchester", "password", "Dorchester, Dorset, GB", "domain.tld/branches/dorchester", "dorchester@domain.tld", "07700900461"));
        branches.add(new Branch("poole", "password", "Poole, Dorset, GB", "domain.tld/branches/poole", "poole@domain.tld", "07700900461"));
        branches.add(new Branch("bournemouth", "password", "Bournemouth, Dorset, GB", "domain.tld/branches/bournemouth", "bournemouth@domain.tld", "07700900461"));

        for (int i = 0; i < 15; i++) {
            for (int ii = 0; ii < 4; ii++) {
                if (i % 7 == 0) {
                    properties.add(new PropertyHouse(
                            branches.get(ii),
                            "B: " + ii + " P: " + i,
                            ii % 4 == 0 ? 3 : 2,
                            12345678L,
                            ii % 3 == 0 ? -1 : 98765432L,
                            ii % 4 == 0 ? 3 : 2,
                            i % 2 == 0,
                            i % 5 == 0
                    ));
                } else {
                    properties.add(new PropertyFlat(
                            branches.get(ii),
                            "B: " + ii + " P: " + i,
                            ii % 4 == 0 ? 3 : 2,
                            12345678L,
                            ii % 3 == 0 ? -1 : 98765432L,
                            ii % 4 == 0 ? 3 : 2,
                            i % 5 == 0 ? 50050 : 35000
                    ));
                }
            }
        }

        for (Branch branch : branches) {
            for (Property property : properties) {
                if (property.getBranch() == branch) {
                    branch.addProperty(property);
                }
            }
            dataManager.create(branch);
        }

        logger.log(Level.INFO, "Successfully created seed data for application.");
    }
}
