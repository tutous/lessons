package de.tutous.spring.boot.api.user;

import de.tutous.spring.boot.common.api.Audited;
import de.tutous.spring.boot.common.api.Identified;

public interface UserOut extends UserIn, Audited, Identified<Integer> {

}
