package com.tfml.model.ContractResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 1/10/16.
 */

public class ContractsDataModel {


        @SerializedName("total")
        @Expose
        private Integer total;
        @SerializedName("active")
        @Expose
        private ActiveContractsModel active;
        @SerializedName("terminated")
        @Expose
        private TerminatedContractsModel terminated;

        /**
         *
         * @return
         * The total
         */
        public Integer getTotal() {
            return total;
        }

        /**
         *
         * @param total
         * The total
         */
        public void setTotal(Integer total) {
            this.total = total;
        }

        /**
         *
         * @return
         * The active
         */
        public ActiveContractsModel getActive() {
            return active;
        }

        /**
         *
         * @param active
         * The active
         */
        public void setActive(ActiveContractsModel active) {
            this.active = active;
        }

        /**
         *
         * @return
         * The terminated
         */
        public TerminatedContractsModel getTerminated() {
            return terminated;
        }

        /**
         *
         * @param terminated
         * The terminated
         */
        public void setTerminated(TerminatedContractsModel terminated) {
            this.terminated = terminated;
        }

    }
