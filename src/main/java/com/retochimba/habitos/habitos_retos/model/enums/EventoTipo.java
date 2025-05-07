package com.retochimba.habitos.habitos_retos.model.enums;

public enum EventoTipo {
    INICIO_REGLA,               // Comienzo del sangrado menstrual
    FIN_REGLA,                  // Fin del sangrado
    OVULACION,                  // Día de ovulación (calculado o registrado)
    SANGRADO_INTERMENSTRUAL,   // Sangrado fuera del ciclo
    DOLOR_INTENSO,             // Dolor muy fuerte asociado al ciclo
    CAMBIO_ANIMO_BRUSCO,       // Cambios emocionales marcados
    FLUJO_ANORMAL,             // Flujo distinto al habitual
    FIEBRE,                    // Presencia de fiebre
    OTRO                       // Cualquier otro evento relevante
}