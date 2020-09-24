/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webauthn4j.appattest.validator;

import com.webauthn4j.appattest.validator.attestation.statement.appleappattest.AppleAppAttestStatementValidator;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.validator.CoreRegistrationDataValidator;
import com.webauthn4j.validator.CustomCoreRegistrationValidator;
import com.webauthn4j.validator.attestation.trustworthiness.certpath.CertPathTrustworthinessValidator;
import com.webauthn4j.validator.attestation.trustworthiness.self.DefaultSelfAttestationTrustworthinessValidator;
import com.webauthn4j.validator.attestation.trustworthiness.self.SelfAttestationTrustworthinessValidator;

import java.util.Collections;
import java.util.List;

public class DCAttestationDataValidator extends CoreRegistrationDataValidator{

    public DCAttestationDataValidator(CertPathTrustworthinessValidator certPathTrustworthinessValidator, List<CustomCoreRegistrationValidator> customRegistrationValidatorList, ObjectConverter objectConverter) {
        super(Collections.singletonList(new AppleAppAttestStatementValidator()),
                certPathTrustworthinessValidator, createSelfAttestationTrustWorthinessValidator(), Collections.emptyList(), objectConverter);
    }

    private static SelfAttestationTrustworthinessValidator createSelfAttestationTrustWorthinessValidator(){
        DefaultSelfAttestationTrustworthinessValidator selfAttestationTrustworthinessValidator = new DefaultSelfAttestationTrustworthinessValidator();
        selfAttestationTrustworthinessValidator.setSelfAttestationAllowed(false);
        return selfAttestationTrustworthinessValidator;
    }

}
