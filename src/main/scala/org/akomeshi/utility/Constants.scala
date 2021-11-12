package org.akomeshi
package utility

/**
 * Created by KaNguy - 6/30/2021
 * File org.akomeshi.utility/Constants.scala
 */

case object Constants {
  /**
   * General constants for the library
   */
  val packageName: String = "Akomeshi"
  val packageVersion: String = "0.0.1"

  val APIVersion: Int = 9
  private val portNumber: Int = 443

  val gatewayURL: String = s"wss://gateway.discord.gg:${portNumber.toString}?v=${APIVersion.toString}&encoding=org.akomeshi.json&compress=zlib-stream"
  val apiURL: String = "https://discord.com/api"
  val cdnURL: String = "https://cdn.discordapp.com"
  val inviteURL: String = "https://discord.gg"
  val templateURL: String = "https://discord.new"
  val formatAPIURL: String = s"${this.apiURL}/v${this.APIVersion}"

  val userAgent: String = s"DiscordBot ($packageName, $packageVersion)"

  val $os: String = System.getProperty("os.name")
  val $browser, $device: String = packageName

  val zlibSuffix: Int = 0x0000FFFF

  /**
   * WebSocket Event Opcodes
   * @see [[https://discord.com/developers/docs/topics/opcodes-and-status-codes]]
   */
  val GatewayOpcodes: Map[String, Int] = Map(
    "DISPATCH" -> 0,
    "HEARTBEAT" -> 1,
    "IDENTIFY" -> 2,
    "STATUS_UPDATE" -> 3,
    "VOICE_STATE_UPDATE" -> 4,
    "VOICE_GUILD_PING" -> 5,
    "RESUME" -> 6,
    "RECONNECT" -> 7,
    "REQUEST_GUILD_MEMBERS" -> 8,
    "INVALID_SESSION" -> 9,
    "HELLO" -> 10,
    "HEARTBEAT_ACK" -> 11
  )

  /**
   * WebSocket events from the Discord Gateway
   * @see [[https://discord.com/developers/docs/topics/gateway#commands-and-events-gateway-events]]
   * @example {{{ WebSocketEvents.map(x => "READY").head }}}
   */
  val WebSocketEvents: Seq[String] = Seq(
    "READY",
    "RESUMED",
    "APPLICATION_COMMAND_CREATE",
    "APPLICATION_COMMAND_DELETE",
    "APPLICATION_COMMAND_UPDATE",
    "GUILD_CREATE",
    "GUILD_DELETE",
    "GUILD_UPDATE",
    "INVITE_CREATE",
    "INVITE_DELETE",
    "GUILD_MEMBER_ADD",
    "GUILD_MEMBER_REMOVE",
    "GUILD_MEMBER_UPDATE",
    "GUILD_MEMBERS_CHUNK",
    "GUILD_INTEGRATIONS_UPDATE",
    "GUILD_ROLE_CREATE",
    "GUILD_ROLE_DELETE",
    "GUILD_ROLE_UPDATE",
    "GUILD_BAN_ADD",
    "GUILD_BAN_REMOVE",
    "GUILD_EMOJIS_UPDATE",
    "CHANNEL_CREATE",
    "CHANNEL_DELETE",
    "CHANNEL_UPDATE",
    "CHANNEL_PINS_UPDATE",
    "MESSAGE_CREATE",
    "MESSAGE_DELETE",
    "MESSAGE_UPDATE",
    "MESSAGE_DELETE_BULK",
    "MESSAGE_REACTION_ADD",
    "MESSAGE_REACTION_REMOVE",
    "MESSAGE_REACTION_REMOVE_ALL",
    "MESSAGE_REACTION_REMOVE_EMOJI",
    "THREAD_CREATE",
    "THREAD_UPDATE",
    "THREAD_DELETE",
    "THREAD_LIST_SYNC",
    "THREAD_MEMBER_UPDATE",
    "THREAD_MEMBERS_UPDATE",
    "USER_UPDATE",
    "PRESENCE_UPDATE",
    "TYPING_START",
    "VOICE_STATE_UPDATE",
    "VOICE_SERVER_UPDATE",
    "WEBHOOKS_UPDATE",
    "INTERACTION_CREATE",
    "STAGE_INSTANCE_CREATE",
    "STAGE_INSTANCE_UPDATE",
    "STAGE_INSTANCE_DELETE",
    "GUILD_STICKERS_UPDATE",
  )

  /**
   * API Errors with the codes
   * @see [[https://discord.com/developers/docs/topics/opcodes-and-status-codes#json]]
   */
  val APIErrors: Map[String, Int] = Map(
    "UNKNOWN_ACCOUNT" -> 10001,
    "UNKNOWN_APPLICATION" -> 10002,
    "UNKNOWN_CHANNEL" -> 10003,
    "UNKNOWN_GUILD" -> 10004,
    "UNKNOWN_INTEGRATION" -> 10005,
    "UNKNOWN_INVITE" -> 10006,
    "UNKNOWN_MEMBER" -> 10007,
    "UNKNOWN_MESSAGE" -> 10008,
    "UNKNOWN_OVERWRITE" -> 10009,
    "UNKNOWN_PROVIDER" -> 10010,
    "UNKNOWN_ROLE" -> 10011,
    "UNKNOWN_TOKEN" -> 10012,
    "UNKNOWN_USER" -> 10013,
    "UNKNOWN_EMOJI" -> 10014,
    "UNKNOWN_WEBHOOK" -> 10015,
    "UNKNOWN_WEBHOOK_SERVICE" -> 10016,
    "UNKNOWN_SESSION" -> 10020,
    "UNKNOWN_BAN" -> 10026,
    "UNKNOWN_SKU" -> 10027,
    "UNKNOWN_STORE_LISTING" -> 10028,
    "UNKNOWN_ENTITLEMENT" -> 10029,
    "UNKNOWN_BUILD" -> 10030,
    "UNKNOWN_LOBBY" -> 10031,
    "UNKNOWN_BRANCH" -> 10032,
    "UNKNOWN_STORE_DIRECTORY_LAYOUT" -> 10033,
    "UNKNOWN_REDISTRIBUTABLE" -> 10036,
    "UNKNOWN_GIFT_CODE" -> 10038,
    "UNKNOWN_GUILD_TEMPLATE" -> 10057,
    "UNKNOWN_DISCOVERABLE_SERVER_CATEGORY" -> 10059,
    "UNKNOWN_STICKER" -> 10060,
    "UNKNOWN_INTERACTION" -> 10062,
    "UNKNOWN_APPLICATION_COMMAND" -> 10063,
    "UNKNOWN_APPLICATION_COMMAND_PERMISSIONS" -> 10066,
    "UNKNOWN_STAGE_INSTANCE" -> 10067,
    "UNKNOWN_GUILD_MEMBER_VERIFICATION_FORM" -> 10068,
    "UNKNOWN_GUILD_WELCOME_SCREEN" -> 10069,
    "BOT_PROHIBITED_ENDPOINT" -> 20001,
    "BOT_ONLY_ENDPOINT" -> 20002,
    "CANNOT_SEND_EXPLICIT_CONTENT" -> 20009,
    "NOT_AUTHORIZED" -> 20012,
    "SLOWMODE_RATE_LIMIT" -> 20016,
    "ACCOUNT_OWNER_ONLY" -> 20018,
    "ANNOUNCEMENT_EDIT_LIMIT_EXCEEDED" -> 20022,
    "CHANNEL_HIT_WRITE_RATELIMIT" -> 20028,
    "CONTENT_NOT_ALLOWED" -> 20031,
    "GUILD_PREMIUM_LEVEL_TOO_LOW" -> 20035,
    "MAXIMUM_GUILDS" -> 30001,
    "MAXIMUM_FRIENDS" -> 30002,
    "MAXIMUM_PINS" -> 30003,
    "MAXIMUM_RECIPIENTS" -> 30004,
    "MAXIMUM_ROLES" -> 30005,
    "MAXIMUM_WEBHOOKS" -> 30007,
    "MAXIMUM_EMOJIS" -> 30008,
    "MAXIMUM_REACTIONS" -> 30010,
    "MAXIMUM_CHANNELS" -> 30013,
    "MAXIMUM_ATTACHMENTS" -> 30015,
    "MAXIMUM_INVITES" -> 30016,
    "MAXIMUM_ANIMATED_EMOJIS" -> 30018,
    "MAXIMUM_SERVER_MEMBERS" -> 30019,
    "MAXIMUM_NUMBER_OF_SERVER_CATEGORIES" -> 30030,
    "GUILD_ALREADY_HAS_TEMPLATE" -> 30031,
    "MAXIMUM_THREAD_PARTICIPANTS" -> 30033,
    "MAXIMUM_NON_GUILD_MEMBERS_BANS" -> 30035,
    "MAXIMUM_BAN_FETCHES" -> 30037,
    "MAXIMUM_NUMBER_OF_STICKERS_REACHED" -> 30039,
    "UNAUTHORIZED" -> 40001,
    "ACCOUNT_VERIFICATION_REQUIRED" -> 40002,
    "DIRECT_MESSAGES_TOO_FAST" -> 40003,
    "REQUEST_ENTITY_TOO_LARGE" -> 40005,
    "FEATURE_TEMPORARILY_DISABLED" -> 40006,
    "USER_BANNED" -> 40007,
    "TARGET_USER_NOT_CONNECTED_TO_VOICE" -> 40032,
    "ALREADY_CROSSPOSTED" -> 40033,
    "MISSING_ACCESS" -> 50001,
    "INVALID_ACCOUNT_TYPE" -> 50002,
    "CANNOT_EXECUTE_ON_DM" -> 50003,
    "EMBED_DISABLED" -> 50004,
    "CANNOT_EDIT_MESSAGE_BY_OTHER" -> 50005,
    "CANNOT_SEND_EMPTY_MESSAGE" -> 50006,
    "CANNOT_MESSAGE_USER" -> 50007,
    "CANNOT_SEND_MESSAGES_IN_VOICE_CHANNEL" -> 50008,
    "CHANNEL_VERIFICATION_LEVEL_TOO_HIGH" -> 50009,
    "OAUTH2_APPLICATION_BOT_ABSENT" -> 50010,
    "MAXIMUM_OAUTH2_APPLICATIONS" -> 50011,
    "INVALID_OAUTH_STATE" -> 50012,
    "MISSING_PERMISSIONS" -> 50013,
    "INVALID_AUTHENTICATION_TOKEN" -> 50014,
    "NOTE_TOO_LONG" -> 50015,
    "INVALID_BULK_DELETE_QUANTITY" -> 50016,
    "CANNOT_PIN_MESSAGE_IN_OTHER_CHANNEL" -> 50019,
    "INVALID_OR_TAKEN_INVITE_CODE" -> 50020,
    "CANNOT_EXECUTE_ON_SYSTEM_MESSAGE" -> 50021,
    "CANNOT_EXECUTE_ON_CHANNEL_TYPE" -> 50024,
    "INVALID_OAUTH_TOKEN" -> 50025,
    "MISSING_OAUTH_SCOPE" -> 50026,
    "INVALID_WEBHOOK_TOKEN" -> 50027,
    "INVALID_ROLE" -> 50028,
    "INVALID_RECIPIENTS" -> 50033,
    "BULK_DELETE_MESSAGE_TOO_OLD" -> 50034,
    "INVALID_FORM_BODY" -> 50035,
    "INVITE_ACCEPTED_TO_GUILD_NOT_CONTAINING_BOT" -> 50036,
    "INVALID_API_VERSION" -> 50041,
    "FILE_UPLOADED_EXCEEDS_MAXIMUM_SIZE" -> 50045,
    "INVALID_FILE_UPLOADED" -> 50046,
    "CANNOT_SELF_REDEEM_GIFT" -> 50054,
    "PAYMENT_SOURCE_REQUIRED" -> 50070,
    "CANNOT_DELETE_COMMUNITY_REQUIRED_CHANNEL" -> 50074,
    "INVALID_STICKER_SENT" -> 50081,
    "INVALID_OPERATION_ON_ARCHIVED_THREAD" -> 50083,
    "INVALID_THREAD_NOTIFICATION_SETTINGS" -> 50084,
    "PARAMETER_EARLIER_THAN_CREATION" -> 50085,
    "TWO_FACTOR_REQUIRED" -> 60003,
    "NO_USERS_WITH_DISCORDTAG_EXIST" -> 80004,
    "REACTION_BLOCKED" -> 90001,
    "RESOURCE_OVERLOADED" -> 130000,
    "STAGE_ALREADY_OPEN" -> 150006,
    "MESSAGE_ALREADY_HAS_THREAD" -> 160004,
    "THREAD_LOCKED" -> 160005,
    "MAXIMUM_ACTIVE_THREADS" -> 160006,
    "MAXIMUM_ACTIVE_ANNOUNCEMENT_THREADS" -> 160007,
    "INVALID_JSON_FOR_UPLOADED_LOTTIE_FILE" -> 170001,
    "UPLOADED_LOTTIES_CANNOT_CONTAIN_RASTERIZED_IMAGES" -> 170002,
    "STICKER_MAXIMUM_FRAMERATE_EXCEEDED" -> 170003,
    "STICKER_FRAME_COUNT_EXCEEDS_MAXIMUM_OF_1000_FRAMES" -> 170004,
    "LOTTIE_ANIMATION_MAXIMUM_DIMENSIONS_EXCEEDED" -> 170005,
    "STICKER_FRAME_RATE_IS_TOO_SMALL_OR_TOO_LARGE" -> 170006,
    "STICKER_ANIMATION_DURATION_EXCEEDS_MAXIMUM_OF_5_SECONDS" -> 170007
  )

  /**
   * User Flags
   * @see [[https://discord.com/developers/docs/resources/user#user-object-user-flags]]
   */
  val userFlags: Map[String, Int] = Map(
    "DISCORD_EMPLOYEE" -> (1 << 0),
    "PARTNERED_SERVER_OWNER" -> (1 << 1),
    "HYPESQUAD_EVENTS" -> (1 << 2),
    "BUGHUNTER_LEVEL_1" -> (1 << 3),
    "HOUSE_BRAVERY" -> (1 << 6),
    "HOUSE_BRILLIANCE" -> (1 << 7),
    "HOUSE_BALANCE" -> (1 << 8),
    "EARLY_SUPPORTER" -> (1 << 9),
    "TEAM_USER" -> (1 << 10),
    "BUGHUNTER_LEVEL_2" -> (1 << 14),
    "VERIFIED_BOT" -> (1 << 16),
    "EARLY_VERIFIED_BOT_DEVELOPER" -> (1 << 17),
    "DISCORD_CERTIFIED_MODERATOR" -> (1 << 18)
  )

  /**
   * Gateway intents
   * @see [[https://discord.com/developers/docs/topics/gateway#list-of-intents]]
   */
  val intents: Map[String, Int] = Map(
    "GUILDS" -> (1 << 0),
    "GUILD_MEMBERS" -> (1 << 1),
    "GUILD_BANS" -> (1 << 2),
    "GUILD_EMOJIS_AND_STICKERS" -> (1 << 3),
    "GUILD_INTEGRATIONS" -> (1 << 4),
    "GUILD_WEBHOOKS" -> (1 << 5),
    "GUILD_INVITES" -> (1 << 6),
    "GUILD_VOICE_STATES" -> (1 << 7),
    "GUILD_PRESENCES" -> (1 << 8),
    "GUILD_MESSAGES" -> (1 << 9),
    "GUILD_MESSAGE_REACTIONS" -> (1 << 10),
    "GUILD_MESSAGE_TYPING" -> (1 << 11),
    "DIRECT_MESSAGES" -> (1 << 12),
    "DIRECT_MESSAGE_REACTIONS" -> (1 << 13),
    "DIRECT_MESSAGE_TYPING" -> (1 << 14)
  )

  val permissionFlags: Map[String, Int] = Map(
    "CREATE_INSTANT_INVITE" -> (1 << 0),
    "KICK_MEMBERS" -> (1 << 1),
    "BAN_MEMBERS" -> (1 << 2),
    "ADMINISTRATOR" -> (1 << 3),
    "MANAGE_CHANNELS" -> (1 << 4),
    "MANAGE_GUILD" -> (1 << 5),
    "ADD_REACTIONS" -> (1 << 6),
    "VIEW_AUDIT_LOG" -> (1 << 7),
    "PRIORITY_SPEAKER" -> (1 << 8),
    "STREAM" -> (1 << 9),
    "VIEW_CHANNEL" -> (1 << 10),
    "SEND_MESSAGES" -> (1 << 11),
    "SEND_TTS_MESSAGES" -> (1 << 12),
    "MANAGE_MESSAGES" -> (1 << 13),
    "EMBED_LINKS" -> (1 << 14),
    "ATTACH_FILES" -> (1 << 15),
    "READ_MESSAGE_HISTORY" -> (1 << 16),
    "MENTION_EVERYONE" -> (1 << 17),
    "USE_EXTERNAL_EMOJIS" -> (1 << 18),
    "VIEW_GUILD_INSIGHTS" -> (1 << 19),
    "CONNECT" -> (1 << 20),
    "SPEAK" -> (1 << 21),
    "MUTE_MEMBERS" -> (1 << 22),
    "DEAFEN_MEMBERS" -> (1 << 23),
    "MOVE_MEMBERS" -> (1 << 24),
    "USE_VAD" -> (1 << 25),
    "CHANGE_NICKNAME" -> (1 << 26),
    "MANAGE_NICKNAMES" -> (1 << 27),
    "MANAGE_ROLES" -> (1 << 28),
    "MANAGE_WEBHOOKS" -> (1 << 29),
    "MANAGE_EMOJIS_AND_STICKERS" -> (1 << 30),
    "USE_APPLICATION_COMMANDS" -> (1 << 31),
    "REQUEST_TO_SPEAK" -> (1 << 32),
    "MANAGE_THREADS" -> (1 << 34),
    "CREATE_PUBLIC_THREADS" -> (1 << 35),
    "CREATE_PRIVATE_THREADS" -> (1 << 36),
    "USE_EXTERNAL_STICKERS" -> (1 << 37),
    "SEND_MESSAGES_IN_THREADS" -> (1 << 38),
    "START_EMBEDDED_ACTIVITIES" -> (1 << 39)
  )
}
