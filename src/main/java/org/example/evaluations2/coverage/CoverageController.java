package org.example.evaluations2.coverage;

import org.springframework.http.ResponseEntity;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/upload")
public class CoverageController {
    private final String TEMPLATE_PROJECT_PATH = "/Users/anuragkhanna/Desktop/Naman/IntelliJ/Evaluations2";
    private final String TEMP_DIR = "/tmp/student_submissions";

    @PostMapping
    public ResponseEntity<String> submitFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("className") String className
    ) {
        try {
            Path studentDir = Files.createTempDirectory(Path.of(TEMP_DIR), "submission_");

            FileSystemUtils.copyRecursively(new File(TEMPLATE_PROJECT_PATH), studentDir.toFile());

            String classPath = className.replace('.', '/') + ".java";
            Path targetFile = studentDir.resolve("src/main/java").resolve(classPath);
            Files.createDirectories(targetFile.getParent());
            Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);

            ProcessBuilder pb = new ProcessBuilder("mvn", "clean", "test");
            pb.directory(studentDir.toFile());
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();

            Path jacocoXml = studentDir.resolve("target/site/jacoco/jacoco.xml");
            if (!Files.exists(jacocoXml)) {
                return ResponseEntity.internalServerError().body("JaCoCo report not found.");
            }

            double coverage = calculateCoverage(jacocoXml.toFile(), className);

            FileSystemUtils.deleteRecursively(studentDir);

            return ResponseEntity.ok("Coverage for " + className + ": " + coverage + "%");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    private double calculateCoverage(File jacocoXmlFile, String className) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(jacocoXmlFile);
        doc.getDocumentElement().normalize();

        String classPath = className.replace('.', '/');
        var classElements = doc.getElementsByTagName("class");
        for (int i = 0; i < classElements.getLength(); i++) {
            Element cls = (Element) classElements.item(i);
            String nameAttr = cls.getAttribute("name");
            if (nameAttr.equals(classPath)) {
                Element counter = (Element) cls.getElementsByTagName("counter").item(0); // LINE counter
                int missed = Integer.parseInt(counter.getAttribute("missed"));
                int covered = Integer.parseInt(counter.getAttribute("covered"));
                int total = covered + missed;
                return total == 0 ? 0 : (covered * 100.0 / total);
            }
        }
        return 0.0;
    }
}
