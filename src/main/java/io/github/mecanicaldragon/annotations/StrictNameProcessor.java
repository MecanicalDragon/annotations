package io.github.mecanicaldragon.annotations;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * Processor for {@link StrictName}.
 *
 * @author Stanislav Tretyakov
 * 11.07.2022
 */
@AutoService(Processor.class)
public class StrictNameProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(StrictName.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_11;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            for (Element annotatedElement : annotatedElements) {
                final var simpleName = annotatedElement.getSimpleName().toString();
                final var requiredName = annotatedElement.getAnnotation(StrictName.class).value();
                if (!simpleName.equals(requiredName)) {
                    final var type = annotatedElement.getKind().name();
                    final var message =
                        String.format("Compilation failed! %s <%s> must have @StrictName <%s>!", type, simpleName, requiredName);
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
                    throw new RuntimeException(message);
                }
            }
        }
        return true;
    }
}
