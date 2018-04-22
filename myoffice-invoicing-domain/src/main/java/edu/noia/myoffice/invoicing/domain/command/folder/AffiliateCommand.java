package edu.noia.myoffice.invoicing.domain.command.folder;

import edu.noia.myoffice.invoicing.domain.vo.Affiliate;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AffiliateCommand implements FolderCommand {

    @NonNull
    FolderId folderId;
    @NonNull
    Affiliate affiliate;
}
