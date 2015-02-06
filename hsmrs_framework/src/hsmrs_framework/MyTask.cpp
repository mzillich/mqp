#include "hsmrs_implementations/MyTask.h"

MyTask::MyTask(int id, double priority)
	{
		this->id = id;
		this->priority = priority;
		subtasks = new std::vector<Task*>();
		owners = new std::vector<std::string>();
		weights = new MyAttributeWeights();
		progress = new MyProgress();
	}

	void MyTask::addOwner(std::string agent)
	{
		owners->push_back(agent);
	}

	std::map<std::string, double>* MyTask::getAttributeWeights()
	{
		return weights->getWeights();
	}

	int MyTask::getID()
	{
		return id;
	}

	int MyTask::getMaxOwners()
	{
		return 1;
	}

	int MyTask::getMinOwners()
	{
		return 1;
	}

	//TODO this implementation is memory leaky
	std::vector<std::string> MyTask::getOwners()
	{
		return *owners;
	}

	double MyTask::getPriority()
	{
		return priority;
	}

	std::vector<Task*>* MyTask::getSubtasks()
	{
		return new std::vector<Task*>(*subtasks);
	}

	bool MyTask::isReady()
	{
		return true;
	}

	void MyTask::removeOwner(std::string name)
	{
		for (int i = 0; i < owners->size(); i++)
		{
			if ((*owners)[i] == name)
			{
				owners->erase(owners->begin() + i);
				return;
			}
		}
	}

	void MyTask::setPriority(double val)
	{
		priority = val;
	}

	void MyTask::setProgress(double val)
	{
		progress->set(val);
	}

