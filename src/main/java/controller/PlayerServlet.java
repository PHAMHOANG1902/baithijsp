package controller;

import dao.PlayerDAO;
import model.Player;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/players")
public class PlayerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(PlayerServlet.class.getName());

    private PlayerDAO playerDAO;

    @Override
    public void init() {
        playerDAO = new PlayerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Player> players = playerDAO.getAllPlayers();
            request.setAttribute("players", players);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tải danh sách cầu thủ", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải danh sách cầu thủ.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String fullName = request.getParameter("fullName");
        String age = request.getParameter("age");
        int indexId;

        try {
            indexId = Integer.parseInt(request.getParameter("indexId"));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Giá trị indexId không hợp lệ: " + request.getParameter("indexId"), e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Giá trị indexId không hợp lệ.");
            return;
        }

        if (name == null || name.trim().isEmpty() ||
                fullName == null || fullName.trim().isEmpty() ||
                age == null || age.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        Player newPlayer = new Player(0, name, fullName, age, indexId);
        try {
            playerDAO.insertPlayer(newPlayer);
            response.sendRedirect("players");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi thêm cầu thủ", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi thêm cầu thủ.");
        }
    }
}
