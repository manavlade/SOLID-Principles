package DesignPatterns.Prototype;

import java.util.ArrayList;
import java.util.List;

class SarTemplate implements Cloneable {
    private String reportingBank;
    private String FiuCode;
    private String regulatoryHeader;
    private String submissionFormat;
    private List<String> findings;

    public SarTemplate(String reportingBank, String fiuCode, String regulatoryHeader, String submissionFormat) {
        this.reportingBank = reportingBank;
        this.FiuCode = fiuCode;
        this.regulatoryHeader = regulatoryHeader;
        this.submissionFormat = submissionFormat;
        // this.findings = findings; wrong becuase this creates a shallow copy whuch
        // creates a new sheet every time
        this.findings = new ArrayList<>();

        // This expensive work only happens once
        System.out.println("Fetching bank details from DB...");
        System.out.println("Loading regulatory headers...");
        System.out.println("Validating FIU submission format...");
    }

    private SarTemplate(SarTemplate original) {
        this.reportingBank = original.reportingBank;
        this.FiuCode = original.FiuCode;
        this.regulatoryHeader = original.regulatoryHeader;
        this.submissionFormat = original.submissionFormat;

        // DEEP COPY — new list, same contents
        // If we wrote this.findings = original.findings
        // that would be shallow copy — shared reference, shared disaster
        this.findings = new ArrayList<>(original.findings);
    }

    @Override
    public SarTemplate clone() {
        return new SarTemplate(this);
    }

    public void addFinding(String finding) {
        this.findings.add(finding);
    }

    public void setReportingOfficer(String officer) {
        // Case-specific — set after cloning
        System.out.println("Officer assigned: " + officer);
    }

    @Override
    public String toString() {
        return "SAR{" +
                "bank='" + reportingBank + '\'' +
                ", fiu='" + FiuCode + '\'' +
                ", findings=" + findings +
                '}';
    }

}

public class Follows {
    public static void main(String[] args) {

        System.out.println("=== Building master template ===");
        SarTemplate masterTemplate = new SarTemplate(
                "IDFC First Bank", "FIU-IND-001",
                "PMLA 2002 Header", "JSON-v2");
        System.out.println("\n=== Generating case reports (no DB calls) ===");

        // Case 001 — clone, customize, independent
        SarTemplate case001 = masterTemplate.clone();
        case001.setReportingOfficer("Officer Sharma");
        case001.addFinding("Velocity breach: 3 transactions of ₹9.5L in 7 minutes");
        case001.addFinding("Structuring pattern detected — amounts just below ₹10L threshold");

        // Case 002 — clone, customize, completely independent
        SarTemplate case002 = masterTemplate.clone();
        case002.setReportingOfficer("Officer Mehta");
        case002.addFinding("Login from Eastern Europe IP on dormant account");
        case002.addFinding("₹50,000 transfer to first-time beneficiary at 2 AM");

        // Prove they are independent — case002's findings not in case001
        System.out.println("\nCase 001: " + case001);
        System.out.println("Case 002: " + case002);

        // Master template is untouched
        System.out.println("Master:   " + masterTemplate);

    }

}
