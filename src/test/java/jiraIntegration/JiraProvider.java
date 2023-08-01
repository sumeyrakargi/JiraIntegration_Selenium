package jiraIntegration;

import net.rcarz.jiraclient.*;

public class JiraProvider {
    private JiraClient jira;
    private String project;

    public JiraProvider(String jiraUrl, String username, String password, String project) {
        // create basic authentication object
        BasicCredentials creds = new BasicCredentials(username, password);
        // initialize the jira client with the url and the credentials
        jira = new JiraClient(jiraUrl, creds);
        this.project = project;
    }

    public void createJiraIssue(String issueType, String summary, String description, String reporterName) {
        /* Create a new issue. */
        try {
            Issue.FluentCreate newIssueFluentCreate = jira.createIssue(project, issueType);
            // Add the summary
            newIssueFluentCreate.field(Field.SUMMARY, summary);
            // Add the description
            newIssueFluentCreate.field(Field.DESCRIPTION, description);
            // Add the reporter
            newIssueFluentCreate.field(Field.REPORTER, reporterName);
            // create the issue in the jira server
            Issue newIssue = newIssueFluentCreate.execute();

            System.out.println("New issue created. Jira ID : " + newIssue);

        } catch (JiraException e) {
            e.printStackTrace();
        }
    }

}
