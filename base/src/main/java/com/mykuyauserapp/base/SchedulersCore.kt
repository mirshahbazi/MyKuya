package com.mykuyauserapp.base

import io.reactivex.rxjava3.core.Scheduler
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi(MAM)
 */
data class SchedulersCore(
    val main: Scheduler,
    val io: Scheduler
)