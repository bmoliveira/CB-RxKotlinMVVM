package com.boliveira.crunchbase.model

import com.google.gson.annotations.SerializedName
import io.mironov.smuggler.AutoParcelable
import java.util.*

object Model {
    data class ODMOrganizations(var metadata: ODMMetadata, val data: ODMData): AutoParcelable

    data class ODMMetadata(val version: Int,
                           @SerializedName("www_path_prefix") val wwwPrefix: String,
                           @SerializedName("api_path_prefix") val apiPrefix: String,
                           @SerializedName("image_path_prefix") val imagePrefix: String) : AutoParcelable

    data class ODMData(val paging: ODMDataPaging, val items: Array<Item>) : AutoParcelable

    data class ODMDataPaging(@SerializedName("total_items") val totalItems: Int,
                             @SerializedName("number_of_pages") val totalPages: Int,
                             @SerializedName("current_page") val currentPage: Int,
                             @SerializedName("items_per_page") val perPage: Int) : AutoParcelable

    data class Item(
            @SerializedName("uuid") val identifier: String,
            @SerializedName("type") val type: String,
            @SerializedName("properties") val properties: ItemProperties) : AutoParcelable

    data class ItemProperties(
            @SerializedName("web_path") val webLinkSuffix: String,
            val name: String,
            @SerializedName("primary_role") val primaryRole: String,
            @SerializedName("short_description") val description: String,
            @SerializedName("profile_image_url") val profileImage: String?,
            @SerializedName("home_page_url") val homepage: String?,
            @SerializedName("facebook_url") val facebook: String?,
            @SerializedName("twitter_url") val twitter: String?,
            @SerializedName("city_name") val city: String?,
            @SerializedName("region_name") val region: String?,
            @SerializedName("country_name") val country: String?,
            @SerializedName("created_at") val createdAtSeconds: Long,
            @SerializedName("updated_at") val updatedAtSeconds: Long): AutoParcelable {

        val companyRole: OrganizationType
            get() = OrganizationType.fromString(primaryRole)

        val createdAt: Date
            get() = Date(createdAtSeconds * 1000)

        val updatedAt: Date
            get() = Date(createdAtSeconds * 1000)
    }

    enum class OrganizationType(val organization: String?) {
        COMPANY("company"),
        INVESTOR("investor"),
        SCHOOL("school"),
        UNDEFINED("");

        companion object {
            fun fromString(organization: String?): OrganizationType {
                when (organization) {
                    COMPANY.organization -> return COMPANY
                    INVESTOR.organization -> return INVESTOR
                    SCHOOL.organization -> return SCHOOL
                    else -> return UNDEFINED
                }
            }
        }
    }
}