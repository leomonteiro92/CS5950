package lineproject



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AttendantController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Attendant.list(params), model:[attendantInstanceCount: Attendant.count()]
    }

    def show(Attendant attendantInstance) {
        respond attendantInstance
    }

    def create() {
        respond new Attendant(params)
    }

    @Transactional
    def save(Attendant attendantInstance) {
        if (attendantInstance == null) {
            notFound()
            return
        }

        if (attendantInstance.hasErrors()) {
            respond attendantInstance.errors, view:'create'
            return
        }

        attendantInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'attendant.label', default: 'Attendant'), attendantInstance.id])
                redirect attendantInstance
            }
            '*' { respond attendantInstance, [status: CREATED] }
        }
    }

    def edit(Attendant attendantInstance) {
        respond attendantInstance
    }

    @Transactional
    def update(Attendant attendantInstance) {
        if (attendantInstance == null) {
            notFound()
            return
        }

        if (attendantInstance.hasErrors()) {
            respond attendantInstance.errors, view:'edit'
            return
        }

        attendantInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Attendant.label', default: 'Attendant'), attendantInstance.id])
                redirect attendantInstance
            }
            '*'{ respond attendantInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Attendant attendantInstance) {

        if (attendantInstance == null) {
            notFound()
            return
        }

        attendantInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Attendant.label', default: 'Attendant'), attendantInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'attendant.label', default: 'Attendant'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
