package dev.endoy.configuration.yaml.comments;

public interface Commentable {

    /**
     * Adds a comment to the section or value selected by path.
     * Comment will be indented automatically.
     * Multi-line comments can be provided using \n character.
     *
     * @param path    path of desired section or value
     * @param comment the comment to add, # symbol is not needed
     * @param type    the comment type
     */
    void setComment(String path, String comment, CommentType type);

    /**
     * Adds a block comment above the section or value selected by path.
     * Comment will be indented automatically.
     * Multi-line comments can be provided using \n character.
     *
     * @param path    path of desired section or value
     * @param comment the comment to add, # symbol is not needed
     */
    default void setComment(final String path, final String comment) {
        this.setComment(path, comment, CommentType.BLOCK);
    }

    /**
     * Retrieve the comment of the section or value selected by path.
     *
     * @param path path of desired section or value
     * @param type the comment type
     * @return the comment of the section or value selected by path,
     * or null if that path does not have any comment of this type
     */
    String getComment(String path, CommentType type);

    /**
     * Retrieve the block comment of the section or value selected by path.
     *
     * @param path path of desired section or value
     * @return the block comment of the section or value selected by path,
     * or null if that path does not have any comment of type block
     */
    default String getComment(final String path) {
        return this.getComment(path, CommentType.BLOCK);
    }

}
