#include "hsmrs_framework/TaskList.h"

class MyTaskList : public TaskList
{
private:
	std::vector<Task*>* list;
	bool sorted;
public:
	MyTaskList();

	void addTask(Task* task);

	std::vector<Task*> getTasks();

	bool isEmpty();

	Task* pullNextTask();

	Task* removeTask(int id);

	void setPriority(int task, double priority);

	Task* getTask(int id);

	void sortByPriority();
};