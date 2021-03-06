package oop.focus.diary.model;

/**
 * Implementation of {@link ToDoAction}.
 */
public class ToDoActionImpl implements ToDoAction {
    private static final int MAX_LENGTH = 50;
    private String annotation;
    private boolean done;

    /**
     * Instantiates a new to do action, setting is content.
     * @param annotation    the content of the to do action to create
     * @param done  sets the status of to do action(it is done or not)
     */
    public ToDoActionImpl(final String annotation, final boolean done) {
        if (this.hasRightLength(annotation)) {
            this.annotation = annotation;
            this.done = done;
        }
    }

    /**
     * Checks if an annotation is not too long.
     * @param annotation    the annotation whose length is evaluated
     * @return  true if the annotation has a length less than the max chosen, false otherwise.
     */
    private boolean hasRightLength(final String annotation) {
        return annotation.chars().count() <= MAX_LENGTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAnnotation() {
        return this.annotation;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDone() {
        return this.done;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.annotation == null) ? 0 : this.annotation.hashCode());
        return result;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final ToDoActionImpl other = (ToDoActionImpl) obj;
        if (this.annotation == null) {
            return other.annotation == null;
        } else {
            return this.annotation.equals(other.annotation);
        }
    }
}
