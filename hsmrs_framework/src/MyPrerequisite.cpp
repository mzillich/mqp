#include "hsmrs_framework/Prerequisite.h"

class MyPrerequisite: public Prerequisite
{
	MyPrerequisite::MyPrerequisite()
	{
	}

	bool Prerequisite::isFulfilled()
	{
		return true;
	}

	MyPrerequisite::~MyPrerequisite()
	{
	}
};
