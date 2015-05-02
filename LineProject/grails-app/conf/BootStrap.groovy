import lineproject.*

class BootStrap {

    def init = { servletContext ->

    	def s = new Service(name:"Manicure", averageWaitingTime:15)
    	s.save(flush:true)
    }
    def destroy = {
    }
}
