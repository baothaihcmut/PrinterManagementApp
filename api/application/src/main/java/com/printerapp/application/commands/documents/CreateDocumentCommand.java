package com.printerapp.application.commands.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreateDocumentCommand {
    private String name;
    private String link;
    private Boolean save;
}
