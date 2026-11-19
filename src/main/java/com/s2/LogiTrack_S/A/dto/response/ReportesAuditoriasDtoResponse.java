
package com.s2.LogiTrack_S.A.dto.response;

import java.time.LocalDateTime;

public record ReportesAuditoriasDtoResponse(
        Long id,
        LocalDateTime fecha,
        Long usuarioId,
        String username,
        String modulo,
        String tipoAccion,
        Long registroId,
        String descripcion
) {
}
