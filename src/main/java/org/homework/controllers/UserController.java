package org.homework.controllers;

public class UserController implements Controller{
  private static final Logger LOG = LoggerFactory.getLogger(CommentController.class);
  private final Service service;
  private final ObjectMapper objectMapper;
  private final CommentService commentService;

  public CommentController(Service service, ObjectMapper objectMapper, CommentService commentService) {
    this.service = service;
    this.objectMapper = objectMapper;
    this.commentService = commentService;
  }

  @Override
  public void initializeEndpoints() {
    addComment();
    deleteComment();
  }

  private void addComment() {
    service.post("/api/comment/:id", (Request request, Response response) -> {
      response.type("application/json");

      String id = request.params(":id");
      String body = request.body();

      AddCommentRequest addCommentRequest = objectMapper.readValue(body, AddCommentRequest.class);

      try {
        var commentId = commentService.create(Long.parseLong(id), addCommentRequest.text());
        response.status(HttpStatus.CREATED_201);
        LOG.debug("Comment successfully added");
        return objectMapper.writeValueAsString(new AddCommentResponse(commentId));
      } catch (ArticleNotFoundException e) {
        LOG.warn("Cannot add a new comment", e);
        response.status(HttpStatus.BAD_REQUEST_400);
        return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
      } catch (RuntimeException e) {
        LOG.error("Unhandled error", e);
        response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        return objectMapper.writeValueAsString(new ErrorResponse("Internal server error"));
      }
    });
  }
}
