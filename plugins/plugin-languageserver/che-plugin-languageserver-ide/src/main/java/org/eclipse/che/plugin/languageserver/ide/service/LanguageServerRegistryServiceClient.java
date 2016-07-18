package org.eclipse.che.plugin.languageserver.ide.service;

import com.google.inject.Inject;

import org.eclipse.che.api.promises.client.Promise;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.rest.AsyncRequestFactory;
import org.eclipse.che.ide.rest.DtoUnmarshallerFactory;
import org.eclipse.che.plugin.languageserver.shared.lsapi.InitializeResultDTO;
import org.eclipse.che.plugin.languageserver.shared.lsapi.LanguageDescriptionDTO;

import java.util.List;

import static org.eclipse.che.ide.MimeType.APPLICATION_JSON;
import static org.eclipse.che.ide.rest.HTTPHeader.ACCEPT;

/**
 * @author Sven Efftinge
 * @author Anatolii Bazko
 */
public class LanguageServerRegistryServiceClient {

    public static final String BASE_URI = "/languageserver";

    private final DtoUnmarshallerFactory unmarshallerFactory;
    private final AsyncRequestFactory asyncRequestFactory;
    private final AppContext appContext;

    @Inject
    public LanguageServerRegistryServiceClient(DtoUnmarshallerFactory unmarshallerFactory,
                                               AppContext appContext,
                                               AsyncRequestFactory asyncRequestFactory) {
        this.unmarshallerFactory = unmarshallerFactory;
        this.appContext = appContext;
        this.asyncRequestFactory = asyncRequestFactory;
    }

    /**
     * @return all supported languages
     */
    public Promise<List<LanguageDescriptionDTO>> getSupportedLanguages() {
        String requestUrl = appContext.getDevMachine().getWsAgentBaseUrl() + BASE_URI + "/supported";
        return asyncRequestFactory.createGetRequest(requestUrl)
                                  .header(ACCEPT, APPLICATION_JSON)
                                  .send(unmarshallerFactory.newListUnmarshaller(LanguageDescriptionDTO.class));
    }

    /**
     * @return all registered languages
     */
    public Promise<List<InitializeResultDTO>> getRegisteredLanguages() {
        String requestUrl = appContext.getDevMachine().getWsAgentBaseUrl() + BASE_URI + "/registered";
        return asyncRequestFactory.createGetRequest(requestUrl)
                                  .header(ACCEPT, APPLICATION_JSON)
                                  .send(unmarshallerFactory.newListUnmarshaller(InitializeResultDTO.class));
    }

}