package com.licon.redis.core.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Licon
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotpDto implements Serializable {
    private static final long serialVersionUID = 4759679485838532543L;

    @NotNull
    private MfaType mfaType = MfaType.SMS;

    @NotNull
    private String mfaId;
}
