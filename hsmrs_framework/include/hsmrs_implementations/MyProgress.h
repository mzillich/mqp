#ifndef _MY_PROGRESS_H_
#define _MY_PROGRESS_H_

#include "hsmrs_framework/Progress.h"

class MyProgress : public Progress {
private:
	double val;
	
public:
	MyProgress();

	MyProgress(double val);

	virtual double report();

	void set(double val);
};

#endif
