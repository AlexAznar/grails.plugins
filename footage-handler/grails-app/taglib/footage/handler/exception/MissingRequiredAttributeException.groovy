package footage.handler.exception

class MissingRequiredAttributeException extends RuntimeException {

    MissingRequiredAttributeException(String attribute) {
        super(String.format("Required attribute %s is missing.", attribute))
    }

}
