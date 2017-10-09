package cli.command

/**
 * Created by satyan on 10/9/17.
 * CommandParser unit test
 */
class CommandParserTest extends GroovyTestCase {
    void testParse() {
        def command
        //Unknown command
        def msg = shouldFail IllegalArgumentException,{
            command = CommandParser.parse(Arrays.asList("random stuff".split("\\s")))
        }
        assert msg.contains("This command doesn't exist")
        msg = shouldFail IllegalArgumentException,{
            command = CommandParser.parse(Arrays.asList("é2//dfdà@".split("\\s")))
        }
        assert msg.contains("This command doesn't exist")
        msg = shouldFail IllegalArgumentException,{
            command = CommandParser.parse(Arrays.asList("".split("\\s")))
        }
        assert msg.contains("This command doesn't exist")
        command = CommandParser.parse(Arrays.asList("download FILE".split("\\s")))
        assert command instanceof DownloadFileCommand
        msg = shouldFail IllegalArgumentException,{
            command = CommandParser.parse(Arrays.asList("download".split("\\s")))
        }
        assert msg.contains("Name of the video to download must be provided")
        command = CommandParser.parse(Arrays.asList("exit".split("\\s")))
        assert command instanceof ExitCommand
        command = CommandParser.parse(Arrays.asList("info FILE".split("\\s")))
        assert command instanceof FileInformationCommand
        command = CommandParser.parse(Arrays.asList("help".split("\\s")))
        assert command instanceof HelpCommand
        command = CommandParser.parse(Arrays.asList("list files".split("\\s")))
        assert command instanceof ListFilesCommand
        command = CommandParser.parse(Arrays.asList("seeder list".split("\\s")))
        assert command instanceof ListSeedersCommand
        msg = shouldFail IllegalArgumentException,{
            command = CommandParser.parse(Arrays.asList("seeder".split("\\s")))
        }
        assert msg.contains("Unknown seeder command")
        msg = shouldFail IllegalArgumentException,{
            command = CommandParser.parse(Arrays.asList("seeder random".split("\\s")))
        }
        assert msg.contains("Unknown seeder command")
        command = CommandParser.parse(Arrays.asList("seeder search KEYWORDS".split("\\s")))
        assert command instanceof ListSeedersCommand
        command = CommandParser.parse(Arrays.asList("play VIDEO".split("\\s")))
        assert command instanceof PlayVideoCommand
        msg = shouldFail IllegalArgumentException,{
            command = CommandParser.parse(Arrays.asList("play".split("\\s")))
        }
        assert msg.contains("Name of the video to play must be provided")
        command = CommandParser.parse(Arrays.asList("subscribe".split("\\s")))
        assert command instanceof SubscribeCommand
    }
}
