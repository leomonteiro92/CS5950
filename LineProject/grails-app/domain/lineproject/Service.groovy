package lineproject

class Service {
	String name
	int averageWaitingTime
    static constraints = {
    	name nullable:false, minSize:3, unique:true
    	averageWaitingTime nullable:false
    }

    double getAverageTime(){
    	//def c = LineEntry.countByServiceAndIsFinished(this, true);
    	
    	return averageWaitingTime;
    	
    	
    }
}
