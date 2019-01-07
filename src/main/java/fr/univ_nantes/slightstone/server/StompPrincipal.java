package fr.univ_nantes.slightstone.server;

import java.security.Principal;

public class StompPrincipal implements Principal {

	private String name;

    public StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
