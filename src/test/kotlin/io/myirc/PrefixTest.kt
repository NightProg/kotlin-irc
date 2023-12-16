package io.myirc

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.myirc.commands.Prefix

class PrefixTest : DescribeSpec({
    describe("Prefix") {
        it("should parse a prefix with a nickname, user and host") {
            val prefix = Prefix.parse(":Nickname!user@hostname")
            prefix shouldBe Prefix("Nickname", "user", "hostname")
        }
        it("should be null with a erroneous prefix") {
            val prefix = Prefix.parse("Nickname#user@hostname")
            prefix shouldBe null
        }
    }
})  {}