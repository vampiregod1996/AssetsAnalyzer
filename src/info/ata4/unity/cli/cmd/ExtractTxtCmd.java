/*
 ** 2014 July 17
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package info.ata4.unity.cli.cmd;

import com.beust.jcommander.Parameters;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
@Parameters(
    commandNames = "extract-txt",
    commandDescription = "Extracts object data to text files."
)
public class ExtractTxtCmd extends DumpCmd {

    public ExtractTxtCmd() {
        setDumpToFiles(true);
    }
}
