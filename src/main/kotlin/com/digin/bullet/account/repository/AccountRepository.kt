package com.digin.bullet.account.repository

import com.digin.bullet.account.domain.entity.Account
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : ReactiveCrudRepository<Account, Long>