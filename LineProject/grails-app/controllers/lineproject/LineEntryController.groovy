package lineproject



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.*

@Transactional(readOnly = true)
class LineEntryController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond LineEntry.list(params), model:[lineEntryInstanceCount: LineEntry.count()]
    }

    def show(LineEntry lineEntryInstance) {
        respond lineEntryInstance
    }

    def create() {

        if (params.uid && params.serviceId){
            def l = new LineEntry();
            def s = Service.findById(params.serviceId);
            if (s){
                l.setUid(params.uid);
                l.setName(params.name);
                l.setService(s);
                l.save(flush:true);
                render '{"status":"success", "id":' + l.id + '}'
                return
            } else {
                render '{"status":"service not found"}'
                return
            }
        } else {
            render '{"status":"error"}'
            return
        }
    }

    /**
    * Return a json showing the amount of time for the 
    * given lineEntry.
    *
    **/
    def getEstimatedTime(LineEntry l){
        if (l){
            def c = LineEntry.countByIsFinishedAndServiceAndDateCreatedLessThan(false, l.service, l.dateCreated);

            render '{"status": "success", "position": ' + c + ', "timeLeft": ' + l.service.getAverageTime() + '}'
        } else {
            render '{"status":"error"}'
        } 
    }

    @Transactional
    def save(LineEntry lineEntryInstance) {
        if (lineEntryInstance == null) {
            notFound()
            return
        }

        if (lineEntryInstance.hasErrors()) {
            respond lineEntryInstance.errors, view:'create'
            return
        }

        lineEntryInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'lineEntry.label', default: 'LineEntry'), lineEntryInstance.id])
                redirect lineEntryInstance
            }
            '*' { respond lineEntryInstance, [status: CREATED] }
        }
    }

    def edit(LineEntry lineEntryInstance) {
        respond lineEntryInstance
    }

    @Transactional
    def update(LineEntry lineEntryInstance) {
        if (lineEntryInstance == null) {
            notFound()
            return
        }

        if (lineEntryInstance.hasErrors()) {
            respond lineEntryInstance.errors, view:'edit'
            return
        }

        lineEntryInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'LineEntry.label', default: 'LineEntry'), lineEntryInstance.id])
                redirect lineEntryInstance
            }
            '*'{ respond lineEntryInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(LineEntry lineEntryInstance) {

        if (lineEntryInstance == null) {
            notFound()
            return
        }

        lineEntryInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'LineEntry.label', default: 'LineEntry'), lineEntryInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'lineEntry.label', default: 'LineEntry'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
