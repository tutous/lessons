package de.tutous.spring.boot.api.dc;

import de.tutous.spring.boot.api.Employee;
import de.tutous.spring.boot.api.MemberOut;
import de.tutous.spring.boot.common.api.Displayed;

public interface MemberFull extends MemberOut, Employee, Displayed
{

}
