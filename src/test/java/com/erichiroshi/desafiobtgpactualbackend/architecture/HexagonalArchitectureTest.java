package com.erichiroshi.desafiobtgpactualbackend.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@SuppressWarnings("unused")
@AnalyzeClasses(packages = "com.erichiroshi.desafiobtgpactualbackend")
public class HexagonalArchitectureTest {

    // Global (using precise paths to avoid false positives)
    private static final String ANY_DOMAIN = "..domain..";
    private static final String ANY_APPLICATION = "..application..";
    private static final String ANY_INFRASTRUCTURE = "..infrastructure..";

    // External frameworks
    private static final String PKG_SPRING = "org.springframework..";
    private static final String PKG_JAKARTA_JPA = "jakarta.persistence..";
    private static final String PKG_JACKSON = "com.fasterxml.jackson..";
    private static final String PKG_RESILIENCE4J = "io.github.resilience4j..";
    private static final String PKG_MICROMETER = "io.micrometer..";
    private static final String PKG_REDIS = "org.springframework.data.redis..";
    private static final String PKG_SPRING_AI = "org.springframework.ai..";
    private static final String PKG_LOMBOK = "lombok..";

    // ── Total Domain Isolation ─────────────────────────────────────────────
    @ArchTest
    public static final ArchRule domain_must_be_pure_java = noClasses()
            .that().resideInAPackage(ANY_DOMAIN)
            .should().dependOnClassesThat().resideInAPackage(PKG_SPRING)
            .orShould().dependOnClassesThat().resideInAPackage(PKG_JAKARTA_JPA)
            .orShould().dependOnClassesThat().resideInAPackage(PKG_JACKSON)
            .orShould().dependOnClassesThat().resideInAPackage(PKG_RESILIENCE4J)
            .orShould().dependOnClassesThat().resideInAPackage(PKG_MICROMETER)
            .orShould().dependOnClassesThat().resideInAPackage(PKG_SPRING_AI)
            .orShould().dependOnClassesThat().resideInAPackage(PKG_LOMBOK)
            .because("The domain is the core of the software. It must be pure Java, agnostic to frameworks");

    @ArchTest
    public static final ArchRule domain_must_not_depend_on_application =
            noClasses()
                    .that().resideInAPackage(ANY_DOMAIN)
                    .should().dependOnClassesThat()
                    .resideInAPackage(ANY_APPLICATION)
                    .because("The domain represents business rules and must not know about application use cases");

    @ArchTest
    public static final ArchRule domain_must_not_depend_on_infrastructure =
            noClasses()
                    .that().resideInAPackage(ANY_DOMAIN)
                    .should().dependOnClassesThat()
                    .resideInAPackage(ANY_INFRASTRUCTURE)
                    .because("The domain must be independent of external technologies and infrastructure details");

    // ── Application Layer Coverage ─────────────────────────────────

    @ArchTest
    public static final ArchRule application_must_not_depend_on_infrastructure = noClasses()
            .that().resideInAPackage(ANY_APPLICATION)
            .should().dependOnClassesThat().resideInAPackage(ANY_INFRASTRUCTURE)
            .orShould().dependOnClassesThat().resideInAPackage(PKG_JAKARTA_JPA)
            .orShould().dependOnClassesThat().resideInAPackage(PKG_REDIS)
            .orShould().dependOnClassesThat().resideInAPackage(PKG_RESILIENCE4J)
            .orShould().dependOnClassesThat().resideInAPackage(PKG_SPRING_AI)
            .because("Use cases depend exclusively on ports (interfaces), never on infrastructure technologies");

    // ── Naming and Placement Conventions (Classic Design) ──────────────────

    @ArchTest
    public static final ArchRule input_ports_must_end_with_port = classes()
            .that().resideInAPackage("..port.in..")
            .should().haveSimpleNameEndingWith("Port")
            .because("Driver ports (port/in) must expose contracts with the 'Port' suffix");

    @ArchTest
    public static final ArchRule output_ports_must_end_with_port = classes()
            .that().resideInAPackage("..port.out..")
            .should().haveSimpleNameEndingWith("Port")
            .because("Driven ports (port/out) must expose contracts with the 'Port' suffix");

    @ArchTest
    public static final ArchRule use_cases_must_be_services_with_suffix = classes()
            .that().resideInAPackage(ANY_APPLICATION)
            .and().haveSimpleNameEndingWith("UseCase")
            .should().beAnnotatedWith(Service.class)
            .because("Use cases orchestrate flows and must be managed as beans by Spring");

    @ArchTest
    public static final ArchRule jpa_entities_and_controllers_must_reside_in_infrastructure = classes()
            .that().areAnnotatedWith(Entity.class)
            .or().areAnnotatedWith(RestController.class)
            .should().resideInAPackage(ANY_INFRASTRUCTURE)
            .because("Database entities and HTTP controllers are pure infrastructure details");

    @ArchTest
    public static final ArchRule adapters_must_reside_in_infrastructure = classes()
            .that().haveSimpleNameEndingWith("Adapter")
            .should().resideInAPackage(ANY_INFRASTRUCTURE)
            .because("Classes with the Adapter suffix plug infrastructure into the application's ports");

    // ── Ports must be interfaces ──────────────────

    @ArchTest
    public static final ArchRule ports_must_be_interfaces =
            classes()
                    .that().resideInAPackage("..port..")
                    .should().beInterfaces()
                    .because("Ports define application contracts and must be represented as interfaces");

    @ArchTest
    public static final ArchRule ports_must_not_be_annotated_with_service =
            noClasses()
                    .that().resideInAPackage("..port..")
                    .should().beAnnotatedWith(Service.class)
                    .because("Ports represent contracts and must not be registered as Spring components");

    @ArchTest
    public static final ArchRule use_cases_must_be_classes =
            classes()
                    .that()
                    .haveSimpleNameEndingWith("UseCase")
                    .should()
                    .notBeInterfaces()
                    .because("Use cases represent concrete application implementations, not contracts");

    // ── Controllers ──────────────────

    @ArchTest
    public static final ArchRule controllers_must_not_access_adapters =
            noClasses()
                    .that()
                    .areAnnotatedWith(RestController.class)
                    .should()
                    .dependOnClassesThat()
                    .haveSimpleNameEndingWith("Adapter")
                    .because("Controllers must depend only on application ports, never on infrastructure implementations");

    @ArchTest
    public static final ArchRule controllers_must_not_access_repositories =
            noClasses()
                    .that()
                    .areAnnotatedWith(RestController.class)
                    .should()
                    .dependOnClassesThat()
                    .haveSimpleNameEndingWith("Repository")
                    .because("Controllers must not access persistence directly; all orchestration must go through the application layer");

    @ArchTest
    public static final ArchRule controllers_must_only_access_application_domain_and_http_packages =
            classes()
                    .that()
                    .areAnnotatedWith(RestController.class)
                    .should()
                    .onlyDependOnClassesThat()
                    .resideInAnyPackage(
                            ANY_APPLICATION,
                            ANY_DOMAIN,
                            "..infrastructure.http..",
                            "java..",
                            PKG_SPRING,
                            PKG_LOMBOK
                    )
                    .because("Controllers must only orchestrate HTTP input, map DTOs, and call the application/domain layer");

    @ArchTest
    public static final ArchRule controllers_must_not_depend_on_concrete_use_cases =
            noClasses()
                    .that()
                    .areAnnotatedWith(RestController.class)
                    .should()
                    .dependOnClassesThat()
                    .haveSimpleNameEndingWith("UseCase")
                    .because("Controllers must depend on input ports, not on concrete use case implementations");

    @ArchTest
    public static final ArchRule controllers_must_not_depend_on_frameworks =
            noClasses()
                    .that()
                    .areAnnotatedWith(RestController.class)
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage(
                            PKG_JAKARTA_JPA,
                            PKG_REDIS,
                            PKG_SPRING_AI,
                            PKG_RESILIENCE4J
                    )
                    .because("Controllers must delegate responsibilities to the application layer and not access technologies directly");

    // ── Repositories ──────────────────

    @ArchTest
    public static final ArchRule repositories_must_reside_in_infrastructure =
            classes()
                    .that()
                    .haveSimpleNameEndingWith("Repository")
                    .should()
                    .resideInAPackage(ANY_INFRASTRUCTURE)
                    .because("Repositories are persistence details and belong exclusively to infrastructure");

    // ── Rules to safeguard DTO mapping ──────────────────

    @ArchTest
    public static final ArchRule dtos_must_reside_in_infrastructure_or_application = classes()
            .that().haveSimpleNameEndingWith("Request")
            .or().haveSimpleNameEndingWith("Input")
            .or().haveSimpleNameEndingWith("Response")
            .or().haveSimpleNameEndingWith("Output")
            .or().haveSimpleNameEndingWith("DTO")
            .should().resideInAnyPackage(ANY_INFRASTRUCTURE, ANY_APPLICATION)
            .because("Input/output DTOs belong to the outer or application layers, never to the domain");

    @ArchTest
    public static final ArchRule domain_must_never_know_about_dtos = noClasses()
            .that().resideInAPackage(ANY_DOMAIN)
            .should().dependOnClassesThat().haveSimpleNameEndingWith("Request")
            .orShould().dependOnClassesThat().haveSimpleNameEndingWith("Input")
            .orShould().dependOnClassesThat().haveSimpleNameEndingWith("Response")
            .orShould().dependOnClassesThat().haveSimpleNameEndingWith("Output")
            .orShould().dependOnClassesThat().haveSimpleNameEndingWith("DTO")
            .because("The domain must be rich and self-sufficient. DTO-to-domain mapping must happen in the DTO or the application layer");
}
