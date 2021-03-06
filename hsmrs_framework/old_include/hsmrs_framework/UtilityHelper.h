/**************************************************************************
 * 					HSMRS Framework - UtilityHelper.h					***
 * 																		***
 * The UtilityHelper class represents an object whose sole purpose is	***
 * to calculate the Utility of an Agent									***
 *************************************************************************/

#pragma once

#include "Agent.h"
#include "Task.h"

class UtilityHelper
{
public:
	/**
	 * This is the constructor for the UtilityHelper class
	 */
	UtilityHelper(void);

	/**
	 * This is the destructor for the UtilityHelper class
	 */
	~UtilityHelper(void);

	/**
	 * Calculates the utility of the given Agent for the given
	 * task.
	 * @param agent The Agent whose utility is being calculated
	 * @param task The Task used to determine the utility.
	 */
	virtual double calculate(Agent* agent, Task* task) = 0;
};

