package com.digin.bullet.account.handler

import com.digin.bullet.account.entitiy.Account
import org.springframework.data.r2dbc.repository.R2dbcRepository

interface AccountRepository : R2dbcRepository<Account, Long>{
}