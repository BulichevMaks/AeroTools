package com.formgrav.aerotools.data.converters

import com.formgrav.aerotools.data.datasourse.room.model.SettingsEntity
import com.formgrav.aerotools.domain.model.Settings


class SettingsDbConvertor {

    fun map(settings: SettingsEntity?): Settings? {
        if (settings != null) {
            return Settings(
                id = settings.id,
                startGrayAngle = settings.startGrayAngle,
                sweepGrayAngle = settings.sweepGrayAngle,
                startGreenAngle = settings.startGreenAngle,
                sweepGreenAngle = settings.sweepGreenAngle,
                startYellowAngle = settings.startYellowAngle,
                sweepYellowAngle = settings.sweepYellowAngle,
                startRedAngle = settings.startRedAngle,
                sweepRedAngle = settings.sweepRedAngle,
            )
        } else return null
    }

    fun map(settings: Settings): SettingsEntity {
        return SettingsEntity(
            id = settings.id,
            startGrayAngle = settings.startGrayAngle,
            sweepGrayAngle = settings.sweepGrayAngle,
            startGreenAngle = settings.startGreenAngle,
            sweepGreenAngle = settings.sweepGreenAngle,
            startYellowAngle = settings.startYellowAngle,
            sweepYellowAngle = settings.sweepYellowAngle,
            startRedAngle = settings.startRedAngle,
            sweepRedAngle = settings.sweepRedAngle,
        )
    }
//
//    fun map(track: TrackEntity): Track {
//        return Track(
//            id = track.id,
//            trackId = track.trackId,
//            trackName = track.trackName,
//            artistName = track.artistName,
//            trackTimeMillis = track.trackTimeMillis,
//            artworkUrl100 = track.artworkUrl100,
//            previewUrl = track.previewUrl,
//            collectionName = track.collectionName,
//            releaseDate = track.releaseDate,
//            primaryGenreName = track.primaryGenreName,
//            country = track.country
//        )
//    }
}