package com.alchemtech.playlistmaker.di

import com.alchemtech.playlistmaker.data.converters.TrackDbConvertor
import org.koin.dsl.module

val dbConvertorModule = module {
    factory<TrackDbConvertor> { TrackDbConvertor() }
}