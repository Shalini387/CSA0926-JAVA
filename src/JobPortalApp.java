import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class JobPortalApp extends JFrame {

    private static final String URL  = "jdbc:mysql://localhost:3306/job_portal";
    private static final String USER = "root";
    private static final String PASS = "shalini"; // change to your MySQL password

    private JTextField jobTitleField, jobDeptField;
    private JTextField appJobIdField, appNameField;
    private JTextField updateAppIdField;
    private JComboBox<String> statusDropdown;
    private JTextField searchJobIdField;
    private JComboBox<String> searchStatusDropdown;
    private JTextArea outputArea;

    public JobPortalApp() {
        setTitle("Job Portal / Recruitment Management System");
        setSize(700, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Post Job", buildPostJobPanel());
        tabs.add("Apply for Job", buildApplyPanel());
        tabs.add("Update Status", buildUpdateStatusPanel());
        tabs.add("Search Applications", buildSearchPanel());
        tabs.add("Shortlisted Report", buildReportPanel());

        outputArea = new JTextArea(10, 60);
        outputArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outputArea);

        add(tabs, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // ---------- Core Module 1: Post Job Opening ----------
    private JPanel buildPostJobPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        jobTitleField = new JTextField();
        jobDeptField = new JTextField();
        JButton postBtn = new JButton("Post Job");

        panel.add(new JLabel("Job Title:"));
        panel.add(jobTitleField);
        panel.add(new JLabel("Department:"));
        panel.add(jobDeptField);
        panel.add(new JLabel(""));
        panel.add(postBtn);

        postBtn.addActionListener(e -> postJob());
        return panel;
    }

    private void postJob() {
        String title = jobTitleField.getText().trim();
        String dept = jobDeptField.getText().trim();
        if (title.isEmpty() || dept.isEmpty()) {
            outputArea.setText("Error: Job title and department are required.");
            return;
        }
        String sql = "INSERT INTO jobs (title, department) VALUES (?, ?)";
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, title);
            ps.setString(2, dept);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    outputArea.setText("Job posted successfully. job_id = " + rs.getInt(1));
                }
            }
            jobTitleField.setText("");
            jobDeptField.setText("");
        } catch (SQLException ex) {
            outputArea.setText("Error posting job: " + ex.getMessage());
        }
    }

    // ---------- Core Module 2: Apply for Job ----------
    private JPanel buildApplyPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        appJobIdField = new JTextField();
        appNameField = new JTextField();
        JButton applyBtn = new JButton("Apply");

        panel.add(new JLabel("Job ID:"));
        panel.add(appJobIdField);
        panel.add(new JLabel("Candidate Name:"));
        panel.add(appNameField);
        panel.add(new JLabel(""));
        panel.add(applyBtn);

        applyBtn.addActionListener(e -> applyForJob());
        return panel;
    }

    private void applyForJob() {
        String jobIdText = appJobIdField.getText().trim();
        String name = appNameField.getText().trim();
        if (jobIdText.isEmpty() || name.isEmpty()) {
            outputArea.setText("Error: Job ID and candidate name are required.");
            return;
        }
        int jobId;
        try {
            jobId = Integer.parseInt(jobIdText);
        } catch (NumberFormatException nfe) {
            outputArea.setText("Error: Job ID must be a number.");
            return;
        }

        String checkSql = "SELECT job_id FROM jobs WHERE job_id = ?";
        String insertSql = "INSERT INTO applications (job_id, candidate_name, status) VALUES (?, ?, 'Applied')";
        try (Connection con = connect()) {
            try (PreparedStatement check = con.prepareStatement(checkSql)) {
                check.setInt(1, jobId);
                try (ResultSet rs = check.executeQuery()) {
                    if (!rs.next()) {
                        outputArea.setText("Error: No job exists with job_id " + jobId);
                        return;
                    }
                }
            }
            try (PreparedStatement ps = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, jobId);
                ps.setString(2, name);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        outputArea.setText("Application submitted. app_id = " + rs.getInt(1) + ", status = Applied");
                    }
                }
            }
            appJobIdField.setText("");
            appNameField.setText("");
        } catch (SQLException ex) {
            outputArea.setText("Error applying: " + ex.getMessage());
        }
    }

    // ---------- Core Module 3: Update Application Status ----------
    private JPanel buildUpdateStatusPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        updateAppIdField = new JTextField();
        statusDropdown = new JComboBox<>(new String[]{"Applied", "Shortlisted", "Rejected", "Hired"});
        JButton updateBtn = new JButton("Update Status");

        panel.add(new JLabel("Application ID:"));
        panel.add(updateAppIdField);
        panel.add(new JLabel("New Status:"));
        panel.add(statusDropdown);
        panel.add(new JLabel(""));
        panel.add(updateBtn);

        updateBtn.addActionListener(e -> updateStatus());
        return panel;
    }

    private void updateStatus() {
        String appIdText = updateAppIdField.getText().trim();
        if (appIdText.isEmpty()) {
            outputArea.setText("Error: Application ID is required.");
            return;
        }
        int appId;
        try {
            appId = Integer.parseInt(appIdText);
        } catch (NumberFormatException nfe) {
            outputArea.setText("Error: Application ID must be a number.");
            return;
        }
        String status = (String) statusDropdown.getSelectedItem();
        String sql = "UPDATE applications SET status = ? WHERE app_id = ?";
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, appId);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                outputArea.setText("Error: No application found with app_id " + appId);
            } else {
                outputArea.setText("Application " + appId + " updated to status: " + status);
            }
            updateAppIdField.setText("");
        } catch (SQLException ex) {
            outputArea.setText("Error updating status: " + ex.getMessage());
        }
    }

    // ---------- Core Module 4: Search Applications by Job/Status ----------
    private JPanel buildSearchPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        searchJobIdField = new JTextField();
        searchStatusDropdown = new JComboBox<>(new String[]{"Any", "Applied", "Shortlisted", "Rejected", "Hired"});
        JButton searchBtn = new JButton("Search");

        panel.add(new JLabel("Job ID (blank = all jobs):"));
        panel.add(searchJobIdField);
        panel.add(new JLabel("Status:"));
        panel.add(searchStatusDropdown);
        panel.add(new JLabel(""));
        panel.add(searchBtn);

        searchBtn.addActionListener(e -> searchApplications());
        return panel;
    }

    private void searchApplications() {
        String jobIdText = searchJobIdField.getText().trim();
        String status = (String) searchStatusDropdown.getSelectedItem();

        StringBuilder sql = new StringBuilder(
                "SELECT a.app_id, j.title, a.candidate_name, a.status " +
                        "FROM applications a JOIN jobs j ON a.job_id = j.job_id WHERE 1=1");
        if (!jobIdText.isEmpty()) sql.append(" AND a.job_id = ?");
        if (!status.equals("Any")) sql.append(" AND a.status = ?");

        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            int idx = 1;
            if (!jobIdText.isEmpty()) ps.setInt(idx++, Integer.parseInt(jobIdText));
            if (!status.equals("Any")) ps.setString(idx++, status);

            try (ResultSet rs = ps.executeQuery()) {
                StringBuilder sb = new StringBuilder("App ID | Job Title | Candidate | Status\n");
                boolean any = false;
                while (rs.next()) {
                    any = true;
                    sb.append(rs.getInt("app_id")).append(" | ")
                            .append(rs.getString("title")).append(" | ")
                            .append(rs.getString("candidate_name")).append(" | ")
                            .append(rs.getString("status")).append("\n");
                }
                outputArea.setText(any ? sb.toString() : "No matching applications found.");
            }
        } catch (SQLException | NumberFormatException ex) {
            outputArea.setText("Error searching: " + ex.getMessage());
        }
    }

    // ---------- Core Module 5: Shortlisted Candidates Report ----------
    private JPanel buildReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton reportBtn = new JButton("Generate Shortlisted Report");
        panel.add(reportBtn, BorderLayout.NORTH);
        reportBtn.addActionListener(e -> shortlistedReport());
        return panel;
    }

    private void shortlistedReport() {
        String sql = "SELECT j.title, a.candidate_name " +
                "FROM applications a JOIN jobs j ON a.job_id = j.job_id " +
                "WHERE a.status = 'Shortlisted' ORDER BY j.title";
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            StringBuilder sb = new StringBuilder("Job Title | Shortlisted Candidate\n");
            boolean any = false;
            while (rs.next()) {
                any = true;
                sb.append(rs.getString("title")).append(" | ").append(rs.getString("candidate_name")).append("\n");
            }
            outputArea.setText(any ? sb.toString() : "No shortlisted candidates yet.");
        } catch (SQLException ex) {
            outputArea.setText("Error generating report: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JobPortalApp().setVisible(true));
    }
}