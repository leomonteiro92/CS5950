package lineproject



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.*

class ServiceController {

    static allowedMethods = [getServices:"GET", save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Service.list(params), model:[serviceInstanceCount: Service.count()]
    }

    def show(Service serviceInstance) {
        respond serviceInstance
    }

    def create() {
        respond new Service(params)
    }

    @Transactional
    def save(Service serviceInstance) {
        if (serviceInstance == null) {
            notFound()
            return
        }

        if (serviceInstance.hasErrors()) {
            respond serviceInstance.errors, view:'create'
            return
        }

        serviceInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'service.label', default: 'Service'), serviceInstance.id])
                redirect serviceInstance
            }
            '*' { respond serviceInstance, [status: CREATED] }
        }
    }

    def edit(Service serviceInstance) {
        respond serviceInstance
    }

    def getServices(){
        render "{ \"serviceList\":" + (Service.list() as JSON) + "}";
    }

    def getEstimatedTime(Service s){
        println s as JSON;
    }

    @Transactional
    def update(Service serviceInstance) {
        if (serviceInstance == null) {
            notFound()
            return
        }

        if (serviceInstance.hasErrors()) {
            respond serviceInstance.errors, view:'edit'
            return
        }

        serviceInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Service.label', default: 'Service'), serviceInstance.id])
                redirect serviceInstance
            }
            '*'{ respond serviceInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Service serviceInstance) {

        if (serviceInstance == null) {
            notFound()
            return
        }

        serviceInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Service.label', default: 'Service'), serviceInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'service.label', default: 'Service'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
