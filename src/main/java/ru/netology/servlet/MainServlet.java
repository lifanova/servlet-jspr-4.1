package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.netology.controller.PostController.APPLICATION_JSON;

public class MainServlet extends HttpServlet {
    private static final String API = "/api/posts";
    private static final String API_D = "/api/posts/\\d+";
    private PostController controller;

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    //  @Override
    //  protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    //resp.getWriter().print("Hello from servlet");
    //resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
    //resp.setHeader("Content-Type", "application/json");
//        resp.setContentType(APPLICATION_JSON);
//
//        final var path = req.getRequestURI();
//        if (path.equals(API)) {
//            controller.all(resp);
//        } else if (path.matches(API_D)) {
//            final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
//            controller.getById(id, resp);
//        } else {
//            resp.getWriter().print("Hello from servlet");
//            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//        }
    //   }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        final var path = req.getRequestURI();
        try {
            if (path.equals(API)) {
                controller.all(resp);
            } else if (path.matches(API_D)) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                controller.getById(id, resp);
            } else {
                resp.getWriter().print("Hello from servlet");
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();

            if (path.equals(API)) {
                controller.save(req.getReader(), resp);
                return;
            }

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();
            final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));

            if (path.matches(API_D)) {
                controller.removeById(id, resp);
                return;
            }

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


}

