package encryptsl.cekuj.net.api.economy.vault

import encryptsl.cekuj.net.LiteEco
import encryptsl.cekuj.net.extensions.isApproachingZero
import net.milkbowl.vault.economy.AbstractEconomy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.util.*

class AdaptiveEconomyVaultAPI(private val liteEco: LiteEco) : AbstractEconomy() {

    companion object {
        private const val BANK_NOT_SUPPORTED_MESSAGE = "LiteEco does not support bank accounts!"
    }

    override fun isEnabled(): Boolean = liteEco.isEnabled

    override fun getName(): String = liteEco.name

    override fun hasBankSupport(): Boolean = false

    override fun fractionalDigits(): Int = -1

    override fun format(amount: Double): String = liteEco.api.formatting(amount)

    override fun currencyNamePlural(): String? {
        return liteEco.config.getString("economy.currency_name")
    }

    override fun currencyNameSingular(): String? {
        return liteEco.config.getString("economy.currency_prefix")
    }

    override fun hasAccount(player: OfflinePlayer?): Boolean {
        return liteEco.api.hasAccount(player!!)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("hasAccount(player)"))
    override fun hasAccount(playerName: String?): Boolean {
        return hasAccount(Bukkit.getOfflinePlayer(playerName.toString()))
    }

    @Deprecated("Deprecated in Java", ReplaceWith("hasAccount(player)"))
    override fun hasAccount(playerName: String?, worldName: String?): Boolean {
        return hasAccount(Bukkit.getOfflinePlayer(playerName.toString()))
    }

    override fun hasAccount(player: OfflinePlayer?, worldName: String?): Boolean {
        return hasAccount(player)
    }

    override fun getBalance(player: OfflinePlayer?): Double {
        return if (hasAccount(player)) liteEco.api.getBalance(player!!) else 0.0
    }

    @Deprecated("Deprecated in Java", ReplaceWith("getBalance(player)"))
    override fun getBalance(playerName: String?): Double {
        return getBalance(Bukkit.getOfflinePlayer(playerName.toString()))
    }

    @Deprecated("Deprecated in Java", ReplaceWith("getBalance(player)"))
    override fun getBalance(playerName: String?, world: String?): Double {
        return getBalance(Bukkit.getOfflinePlayer(playerName.toString()))
    }

    override fun getBalance(player: OfflinePlayer?, world: String?): Double {
        return getBalance(player)
    }

    override fun has(player: OfflinePlayer?, amount: Double): Boolean {
        return liteEco.api.has(player!!, amount)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("has(player, amount)"))
    override fun has(playerName: String?, amount: Double): Boolean {
        return has(Bukkit.getOfflinePlayer(playerName.toString()), amount)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("has(player, amount)"))
    override fun has(playerName: String?, worldName: String?, amount: Double): Boolean {
        return has(Bukkit.getOfflinePlayer(playerName.toString()), amount)
    }

    override fun has(player: OfflinePlayer?, worldName: String?, amount: Double): Boolean {
        return has(player, amount)
    }

    override fun withdrawPlayer(player: OfflinePlayer?, amount: Double): EconomyResponse {
        if (player == null || amount.isApproachingZero()) {
            return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, null)
        }

        return if (has(player, amount)) {
            liteEco.api.withDrawMoney(player, amount)
            EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, null)
        } else {
            EconomyResponse(0.0, getBalance(player), EconomyResponse.ResponseType.FAILURE, null)
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith("withdrawPlayer(player, amount)"))
    override fun withdrawPlayer(playerName: String?, amount: Double): EconomyResponse {
        return withdrawPlayer(Bukkit.getOfflinePlayer(playerName.toString()), amount)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("withdrawPlayer(player, amount)"))
    override fun withdrawPlayer(playerName: String?, worldName: String?, amount: Double): EconomyResponse {
        return withdrawPlayer(Bukkit.getOfflinePlayer(playerName.toString()), amount)
    }

    override fun withdrawPlayer(player: OfflinePlayer?, worldName: String?, amount: Double): EconomyResponse {
        return withdrawPlayer(player, amount)
    }

    override fun depositPlayer(player: OfflinePlayer?, amount: Double): EconomyResponse {
        if (player == null || !hasAccount(player) || amount.isApproachingZero()) {
            return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, null)
        }

        liteEco.api.depositMoney(player, amount)

        return EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, null)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("depositPlayer(player, amount)"))
    override fun depositPlayer(playerName: String?, amount: Double): EconomyResponse {
        return depositPlayer(Bukkit.getOfflinePlayer(playerName.toString()), amount)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("depositPlayer(player, amount)"))
    override fun depositPlayer(playerName: String?, worldName: String?, amount: Double): EconomyResponse {
        return depositPlayer(Bukkit.getOfflinePlayer(playerName.toString()), amount)
    }

    override fun depositPlayer(player: OfflinePlayer?, worldName: String?, amount: Double): EconomyResponse {
        return depositPlayer(player, amount)
    }

    override fun createPlayerAccount(player: OfflinePlayer?): Boolean {
        return liteEco.api.createAccount(player!!, liteEco.config.getDouble("economy.starting_balance"))
    }

    @Deprecated("Deprecated in Java", ReplaceWith("createPlayerAccount(player)"))
    override fun createPlayerAccount(playerName: String?): Boolean {
        return createPlayerAccount(Bukkit.getOfflinePlayer(playerName.toString()))
    }

    @Deprecated("Deprecated in Java", ReplaceWith("createPlayerAccount(player)"))
    override fun createPlayerAccount(playerName: String?, worldName: String?): Boolean {
        return createPlayerAccount(Bukkit.getOfflinePlayer(playerName.toString()))
    }

    override fun createPlayerAccount(player: OfflinePlayer?, worldName: String?): Boolean {
        return createPlayerAccount(player)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("createBank(name, player)"))
    override fun createBank(name: String?, playerName: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, BANK_NOT_SUPPORTED_MESSAGE)
    }

    override fun createBank(name: String?, player: OfflinePlayer?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, BANK_NOT_SUPPORTED_MESSAGE)
    }

    override fun deleteBank(name: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, BANK_NOT_SUPPORTED_MESSAGE)
    }

    override fun bankBalance(name: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, BANK_NOT_SUPPORTED_MESSAGE)
    }

    override fun bankHas(name: String?, amount: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, BANK_NOT_SUPPORTED_MESSAGE)
    }

    override fun bankWithdraw(name: String?, amount: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, BANK_NOT_SUPPORTED_MESSAGE)
    }

    override fun bankDeposit(name: String?, amount: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, BANK_NOT_SUPPORTED_MESSAGE)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("isBankOwner(name, player)"))
    override fun isBankOwner(name: String?, playerName: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, BANK_NOT_SUPPORTED_MESSAGE)
    }

    override fun isBankOwner(name: String?, player: OfflinePlayer?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, BANK_NOT_SUPPORTED_MESSAGE)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("isBankMember(name, player)"))
    override fun isBankMember(name: String?, playerName: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, BANK_NOT_SUPPORTED_MESSAGE)
    }

    override fun isBankMember(name: String?, player: OfflinePlayer?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, BANK_NOT_SUPPORTED_MESSAGE)
    }

    override fun getBanks(): MutableList<String> {
        return Collections.emptyList()
    }
}