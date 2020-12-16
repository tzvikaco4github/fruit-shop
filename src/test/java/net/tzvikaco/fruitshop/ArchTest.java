package net.tzvikaco.fruitshop;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("net.tzvikaco.fruitshop");

        noClasses()
            .that()
                .resideInAnyPackage("net.tzvikaco.fruitshop.service..")
            .or()
                .resideInAnyPackage("net.tzvikaco.fruitshop.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..net.tzvikaco.fruitshop.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
